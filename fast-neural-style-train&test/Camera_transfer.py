from __future__ import print_function
import tensorflow as tf  
import cv2  
from preprocessing import preprocessing_factory
import reader
import time

capture=cv2.VideoCapture(0)

ret,img=capture.read()
shape = img.shape
height = shape[0]
width = shape[1]

with tf.Graph().as_default():
    output_graph_path = './models/wave.pb'
    with tf.gfile.FastGFile(output_graph_path, 'rb') as f:
        graph_def = tf.GraphDef() 
        graph_def.ParseFromString(f.read()) 
        _ = tf.import_graph_def(graph_def, name='') 
    
    with tf.Session() as sess:
        tf.initialize_all_variables().run()
        image_preprocessing_fn, _ = preprocessing_factory.get_preprocessing('vgg_16', is_training=False)
        input_x = sess.graph.get_tensor_by_name("input:0")
        print(input_x)
        output = sess.graph.get_tensor_by_name("output:0")
        print(output)
        generated = tf.cast(output, tf.uint8)
        generated = tf.squeeze(generated, [0])

        while True:
            start_time = time.time()
            ret,img=capture.read()
            image_transfer = sess.run(generated, feed_dict={input_x: img})
            #print(frame)
            #image_transfer = cv2.cvtColor(image_transfer, cv2.COLOR_BGR2RGB)
            cv2.imshow('camera', img)
            cv2.imshow('transfer', image_transfer)
            end_time = time.time()
            time_spend = end_time - start_time
            frame_cal = 1 / time_spend
            #print(time_spend)
            key = cv2.waitKey(1)  
            if key == 27:  
                break
