import 'package:thumbnails/thumbnails.dart';

const DEFAULT_THUMB_QUALITY = 50;
const DEFAULT_IMAGE_TYPE = ThumbFormat.JPEG;

int validateQuality(int choice) {
  if (choice < 10 || choice > 100 || choice == null)
    return DEFAULT_THUMB_QUALITY;
  return choice;
}

int validateType(ThumbFormat type) {
  if (type == null) return 1;
  switch (type) {
    case ThumbFormat.JPEG:
      return 1;
      break;
    case ThumbFormat.PNG:
      return 2;
      break;
    case ThumbFormat.WEBP:
      return 3;
  }
  return 1;
}
