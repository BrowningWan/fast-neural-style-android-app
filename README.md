# fast-neural-style-android-app
A tensorflow implementation for fast neural style! Convert the model file to a pb file then deploy it into a android app!

A tensorflow implementation for [Perceptual Losses for Real-Time Style Transfer and Super-Resolution](https://arxiv.org/abs/1603.08155).

The python code [here](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/fast-neural-style-train%26test) is forked from [hzy46/fast-neural-style-tensorflow](https://github.com/hzy46/fast-neural-style-tensorflow). 

The [eval.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/eval.py) file was edited to product a .pb model file while converting the image. Then we deployed the .pb model into [Camera_transfer.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/Camera_transfer.py), using opencv and a camera to make a real-time style-transfer.
The [Create_pb_file_4Android.py](https://github.com/BrowningWan/fast-neural-style-android-app/blob/master/fast-neural-style-train%26test/Create_pb_file_4Android.py) allows you to create the .pb model used in [AG_Group_tensorflow4Android](https://github.com/BrowningWan/fast-neural-style-android-app/tree/master/AG_Group_tensorflow4Android) project, this project was build with Android Studio.
