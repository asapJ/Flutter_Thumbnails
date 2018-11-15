# thumbnails

A Flutter Plugin to generate thumbnails from videos.

## Compatibility
Android OS only

## Usage

#### Depend on it
add `thumbnails` as a dependency in your pubspec.yaml file.

#### Update Android Permissions
add the lines to AndroidManifest.xml
``` xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
NOTE: for android versions >= 6.0 Request Permission from user at runtime before calling this plugin.

#### Add import statement
Import the library
``` dart
    import 'package:thumbnails/thumbnails.dart';
```

#### Examples
``` dart
    // Fetch thumbnail, store in a specified output folder and return file path
    String thumb = await Thumbnails.getThumbnail(
        thumbOutputFile:
            '[YOUR OUTPUT FOLDER PATH TO SAVE THUMBNAILS]', // creates it if it doesnt already exist
        videoFile: '[VIDEO PATH HERE]',
        imageType: ThumbFormat.PNG,
        quality: 30);


// Fetch thumbnail, store in app's Temporary directory output folder and return file path
    String thumb = await Thumbnails.getThumbnail(
        videoFile: '[VIDEO PATH HERE]',
        imageType: ThumbFormat.JPEG,
        quality: 45);

```