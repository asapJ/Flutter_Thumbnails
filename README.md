# thumbnails

A Flutter Plugin to generate thumbnails from videos on Local Storage.

## Compatibility
Android OS only

## Usage

#### Depend on it
add `thumbnails` as a dependency in your pubspec.yaml file.
``` yaml
        thumbnails: ^1.0.1
```

#### Update Android Permissions
add the lines to AndroidManifest.xml
``` xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
NOTE: for android versions >= 6.0 you need might to request Permission from user at runtime before calling this plugin.
(There are available plugins that handles permissions on DartLang.)

#### Add import statement
Import the library
``` dart
    import 'package:thumbnails/thumbnails.dart';
```

#### Examples
``` dart
    String thumb = await Thumbnails.getThumbnail(
        thumbnailFolder:'[FOLDER PATH TO STORE THUMBNAILS]', // creates the specified path if it doesnt exist
        videoFile: '[VIDEO PATH HERE]',
        imageType: ThumbFormat.PNG,
        quality: 30);

    /*
    * thumbnailFolder property can be omitted if you dont wish to keep the generated thumbails past each usage
    */

```