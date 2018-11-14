import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:meta/meta.dart';
import 'package:flutter/services.dart';
import 'package:thumbnails/validators.dart';

enum ThumbFormat { PNG, JPEG, WEBP }
class Thumbnails {
  String videoFile;
  String thumbOutputFile;
  int imageType;
  int quality;

  Thumbnails(
      {@required this.videoFile,
      @required this.thumbOutputFile,
      this.imageType,
      this.quality});

  static const MethodChannel _channel = const MethodChannel('thumbnails');

 static Future<void> getThumbnail(
      {@required String videoFile,
      @required String thumbOutputFile,
      ThumbFormat imageType,
      int quality}) async {
    var utilMap = <String, dynamic>{
      'videoFilePath': videoFile,
      'thumbFilePath': thumbOutputFile,
      'thumbnailFormat': validateType(imageType),
      'thumbnailQuality': validateQuality(quality)
    };
   await _channel.invokeMethod('getThumbnail', utilMap);
   print('done!!');
  }
  
  
  
}
