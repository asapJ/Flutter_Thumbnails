#import "ThumbnailsPlugin.h"
#import <AVFoundation/AVFoundation.h>

@implementation ThumbnailsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"thumbnails"
                                     binaryMessenger:[registrar messenger]];
    ThumbnailsPlugin* instance = [[ThumbnailsPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
    } else if ([@"getThumbnail" isEqualToString:call.method]) {
        result([self imageFromVideoURL:call.arguments[@"videoFilePath"]]);
    } else {
        result(FlutterMethodNotImplemented);
    }
}

- (NSString *)imageFromVideoURL:(NSString *)contentPath {
    NSURL *videoURL = [NSURL fileURLWithPath:contentPath];

    AVAsset *asset = [AVAsset assetWithURL:videoURL];

    //  Get thumbnail at the very start of the video
    CMTime thumbnailTime = CMTimeMake(60, 60);
    //    thumbnailTime.value = 0.2;

    //  Get image from the video at the given time
    AVAssetImageGenerator *imageGenerator = [[AVAssetImageGenerator alloc] initWithAsset:asset];
    imageGenerator.appliesPreferredTrackTransform = YES;
    imageGenerator.maximumSize = CGSizeMake(1024, 768);

    CGImageRef imageRef = [imageGenerator copyCGImageAtTime:thumbnailTime actualTime:NULL error:NULL];
    UIImage *thumbnail = [UIImage imageWithCGImage:imageRef];
    if (thumbnail != nil){
        NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,
                                                             NSUserDomainMask, YES);
        NSString *documentsDirectory = [paths objectAtIndex:0];
        NSString* pathTemp = [documentsDirectory stringByAppendingPathComponent:videoURL.lastPathComponent.stringByDeletingPathExtension];
        NSString* path = [pathTemp stringByAppendingString:@"_thumb.jpeg" ];
        NSData* data = UIImageJPEGRepresentation(thumbnail, 1);
        [data writeToFile:path atomically:NO];

        return path;
    } else {
        return @"No file created";
    }
}
@end