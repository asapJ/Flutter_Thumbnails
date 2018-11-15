# thumbnails

A Flutter Plugin to generate thumbnails from videos.

## Compatibility
Android OS only

## Usage

#### Depend on it
add `thumbnails` as a dependency in your pubspec.yaml file.

#### Add import statement
Import the library via
``` dart
import 'package:thumbnails/thumbnails.dart';
```

#### Examples
``` dart
    // Fetch thumbnail, store in a specified output folder and return file path
    String thumb = await Thumbnails.getThumbnail(
        thumbOutputFile:
            '[YOUR OUTPUT FILE HERE]',
        videoFile: '[VIDEO PATH HERE]',
        imageType: ThumbFormat.PNG,
        quality: 30);


// Fetch thumbnail, store in app's Temporary directory output folder and return file path
    String thumb = await Thumbnails.getThumbnail(
        videoFile: 'VIDEO PATH HERE',
        imageType: ThumbFormat.JPEG,
        quality: 45);

```