import 'package:thumbnails/thumbnails.dart';

const DEFAULT_THUMB_QUALITY = 50;
const DEFAULT_IMAGE_TYPE = ThumbFormat.JPEG;

int validateQuality(int? choice) {
  if (choice == null || choice < 10 || choice > 100)
    return DEFAULT_THUMB_QUALITY;
  return choice;
}

int validateType(ThumbFormat? type) {
  if (type == null) return 1;
  switch (type) {
    case ThumbFormat.JPEG:
      return 1;
    case ThumbFormat.PNG:
      return 2;
    case ThumbFormat.WEBP:
      return 3;
  }
}
