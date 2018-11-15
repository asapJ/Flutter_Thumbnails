import 'package:flutter/material.dart';
import 'package:thumbnails/thumbnails.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  // Fetch thumbnail and store in a specified output folder
  void _buildThumbToFile() async {
    String thumb = await Thumbnails.getThumbnail(
        thumbOutputFile:
            '/storage/emulated/0/Videos/Thumbnails',
        videoFile: '/storage/emulated/0/Videos/Testvideo.mp4',
        imageType: ThumbFormat.PNG,
        quality: 30);
    print('path to File: $thumb');
  }

// when an output folder is not specified thumbnail are stored in app temporary directory 
  void _buildThumbToCache() async {
    String thumb = await Thumbnails.getThumbnail(
        videoFile: '/storage/emulated/0/Videos/Testvideo.mp4',
        imageType: ThumbFormat.JPEG,
        quality: 30);
    print('Path to cache folder $thumb');
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: Column(
            children: <Widget>[
              RaisedButton(
                  onPressed: _buildThumbToFile, child: Text('Build To File')),
              RaisedButton(
                  onPressed: _buildThumbToCache, child: Text('Build to Cache')),
            ],
          ),
        ),
      ),
    );
  }
}
