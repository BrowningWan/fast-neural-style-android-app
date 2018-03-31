# fast-neural-style-android-app
A tensorflow implementation for fast neural style! Convert the model file to a pb file then deploy it into a android app!

A tensorflow implementation for [Perceptual Losses for Real-Time Style Transfer and Super-Resolution](https://arxiv.org/abs/1603.08155).

The python code [here](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/fast-neural-style-train%26test) is forked from [hzy46/fast-neural-style-tensorflow](https://github.com/hzy46/fast-neural-style-tensorflow). 

The [eval.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/eval.py) file was edited to product a .pb model file while converting the image. Then we deployed the .pb model into [Camera_transfer.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/Camera_transfer.py), using opencv and a camera to make a real-time style-transfer.
The [Create_pb_file_4Android.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/Create_pb_file_4Android.py) allows you to create the .pb model used in [AG_Group_tensorflow4Android](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/AG_Group_tensorflow4Android) project, this project was build with Android Studio.

## Samples:

| project | style | sample |
| :---: | :----: | :----: |
| Real-time style transfer | ![](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/img/wave.jpg) | ![](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/img/ezgif.com-video-to-gif1%20(1).gif) |
| Android_project | ![](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/img/cubist.jpg) | ![](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/img/ezgif.com-video-to-gif.gif) |

## Requirements and Prerequisites:
- Python 2.7.x or Python 3.5.x
- Tensorflow >= 1.0
- python-opencv
- pyyaml
- Android Studio
- JDK

## Use Trained Models:

You can download all the 7 trained models from [Baidu Drive](https://pan.baidu.com/s/1i4GTS4d).

To generate a sample from the model "wave.ckpt-done", run:

```
cd fast-neural-style-train&test
python eval.py --model_file <your path to wave.ckpt-done> --image_file img/test.jpg
```

Then check out generated/res.jpg for the transfer result and models/wave_small1.pb for .pb model file.

To deploy the real-time style transfer, replace your model file path in line 16 in [Camera_transfer.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/Camera_transfer.py), then run:

```
cd fast-neural-style-train&test
python Camera_transfer.py
```

## Train a Model:
To train a model from scratch, you should first download [VGG16 model](http://download.tensorflow.org/models/vgg_16_2016_08_28.tar.gz) from Tensorflow Slim. Extract the file vgg_16.ckpt. Then copy it to the folder pretrained/ :
```
cd fast-neural-style-train&test
mkdir pretrained
cp <your path to vgg_16.ckpt>  pretrained/
```

Then download the [COCO dataset](http://msvocds.blob.core.windows.net/coco2014/train2014.zip). Please unzip it, and you will have a folder named "train2014" with many raw images in it. Then create a symbol link to it:
```
cd <this repo>
ln -s <your path to the folder "train2014"> train2014
```

Train the model of "wave":
```
python train.py -c conf/wave.yml
```

(Optional) Use tensorboard:
```
tensorboard --logdir models/wave/
```

Checkpoints will be written to "models/wave/".

View the [configuration file](https://github.com/hzy46/fast-neural-style-tensorflow/blob/master/conf/wave.yml) for details.

## Train a Model:
To compiling the android project, just open the project [AG_Group_tensorflow4Android](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/AG_Group_tensorflow4Android), then replace your sdk path, click run.
If you want to use your own images or photos as style images, train the .ckpt model file, then run:

```
cd fast-neural-style-train&test
python Create_pb_file_4Android.py --model_file <your path to .ckpt> --image_file <path to any .jpg or .png picture>
```

Then put the .pb model file into [AG_Group_tensorflow4Android/app/src/main/assets/](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/AG_Group_tensorflow4Android/app/src/main/assets), add your button and java code in [MainActivity.java](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/AG_Group_tensorflow4Android/app/src/main/java/com/group/ag/ag_group_tensorflow4android_v11/MainActivity.java). 

Remember you will need to put a corresponding picture for your own button. 

The left AG logo button allows you switch the camera and reset the app after transfer done. 

In line 56 of [MainActivity.java](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/AG_Group_tensorflow4Android/app/src/main/java/com/group/ag/ag_group_tensorflow4android_v11/MainActivity.java), I give a 90 rotatation to the output, some phone may not need this rotatation, just delete this line, or change the parameter to adapt to your phone.

If you like our project, please give us a star, thx!

If you have any questions and suggests, or just want to discuss the deep learning deployed to mobile phone, welcome to join us in WeChat:

| :---: | ![](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/img/223.png) | :----: |

Tips: This project was made for dear Sansa, my dear lover! (you can also change the text shown in app in line 13 of [activity_main.xml](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/AG_Group_tensorflow4Android/app/src/main/res/layout/activity_main.xml), give a suprise to your girl friend XD)
