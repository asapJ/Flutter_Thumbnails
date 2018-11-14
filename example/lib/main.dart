import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:thumbnails/thumbnails.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
   // initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      await Thumbnails.getThumbnail(
        thumbOutputFile: '/storage/emulated/0/Whatsapp/Media/Test${TimeOfDay.now()}.jpg',
        videoFile: '/storage/emulated/0/Whatsapp/Media/.Statuses/Testvid.mp4',
        imageType: ThumbFormat.PNG,
        quality: 30
      );
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: FlatButton(
            onPressed: ()=> initPlatformState(),
            child: Text('Press Me'),
          ),
        ),
      ),
    );
  }
}
