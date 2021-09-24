import 'dart:async';
import 'package:flutter/services.dart';
import 'package:thumbnails/validators.dart';

enum ThumbFormat { PNG, JPEG, WEBP }

class Thumbnails {
  static const MethodChannel _channel = const MethodChannel('thumbnails');

  static Future<String?> getThumbnail(
      {required String videoFile,
      String? thumbnailFolder,
      ThumbFormat? imageType,
      required int? quality}) async {
    var utilMap = <String, dynamic>{
      'videoFilePath': videoFile,
      'thumbFilePath': thumbnailFolder,
      'thumbnailFormat': validateType(imageType),
      'thumbnailQuality': validateQuality(quality)
    };
    String? thumbnailPath =
        await _channel.invokeMethod('getThumbnail', utilMap);
    return thumbnailPath;
  }
}
