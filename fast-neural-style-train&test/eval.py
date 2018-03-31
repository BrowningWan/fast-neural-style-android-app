# coding: utf-8
from __future__ import print_function
import tensorflow as tf
from preprocessing import preprocessing_factory
import reader
import model
import time
import os
import cv2

tf.app.flags.DEFINE_string('loss_model', 'vgg_16', 'The name of the architecture to evaluate. '
                           'You can view all the support models in nets/nets_factory.py')
tf.app.flags.DEFINE_integer('image_size', 256, 'Image size to train.')
tf.app.flags.DEFINE_string("model_file", "models.ckpt", "")
tf.app.flags.DEFINE_string("image_file", "a.jpg", "")

FLAGS = tf.app.flags.FLAGS

def main(_):
    img_read = cv2.imread(FLAGS.image_file)
    shape = img_read.shape
    height = shape[0]
    width = shape[1]

    with tf.Graph().as_default():
        with tf.Session().as_default() as sess:
            X = tf.placeholder(tf.uint8, shape=[None, None, 3], name='input')
            
            # Read image data.
            image_preprocessing_fn, _ = preprocessing_factory.get_preprocessing(
                FLAGS.loss_model,
                is_training=False)
            image0 = reader.get_image_from_cam(X, height, width, image_preprocessing_fn)

            #X = tf.placeholder(tf.float32, shape=[None, None, 3], name='input')

            # Add batch dimension
            image = tf.expand_dims(image0, 0)
            #image = tf.expand_dims(image, 0)

            generated = model.net(image, training=False)
            generated = tf.cast(generated, tf.uint8)

            # Remove batch dimension
            generated = tf.squeeze(generated, [0])

            # Restore model variables.
            saver = tf.train.Saver(tf.global_variables(), write_version=tf.train.SaverDef.V1)
            sess.run([tf.global_variables_initializer(), tf.local_variables_initializer()])
            # Use absolute path
            FLAGS.model_file = os.path.abspath(FLAGS.model_file)
            saver.restore(sess, FLAGS.model_file)

            # Make sure 'generated' directory exists.
            generated_file = 'generated/res.jpg'
            if os.path.exists('generated') is False:
                os.makedirs('generated')

            # Generate and write image data to file.
            with open(generated_file, 'wb') as img:
                start_time = time.time()
                img.write(sess.run(tf.image.encode_jpeg(generated), feed_dict={X: img_read}))
                end_time = time.time()
                # Generated the protobuf file.
                output_graph_def = tf.graph_util.convert_variables_to_constants(sess, sess.graph_def, output_node_names=['output'])
                with tf.gfile.FastGFile('models/wave_small1.pb', mode = 'wb') as f:
                    f.write(output_graph_def.SerializeToString())

                tf.logging.info('Convert done!')
                tf.logging.info('Elapsed time: %fs' % (end_time - start_time))

                tf.logging.info('Done. Please check %s.' % generated_file)


if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run()
