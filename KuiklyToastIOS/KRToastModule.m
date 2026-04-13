#import "KRToastModule.h"
#import <UIKit/UIKit.h>

#if __has_include(<OpenKuiklyIOSRender/NSObject+KR.h>)
#import <OpenKuiklyIOSRender/NSObject+KR.h>
#elif __has_include(<KuiklyIOSRender/NSObject+KR.h>)
#import <KuiklyIOSRender/NSObject+KR.h>
#endif

@implementation KRToastModule

@synthesize hr_rootView;

- (void)showToast:(NSDictionary *)args {
    NSDictionary *params = [args[KR_PARAM_KEY] hr_stringToDictionary];
    NSString *text = params[@"text"] ?: @"";
    NSInteger duration = [params[@"duration"] integerValue];
    NSInteger gravity = [params[@"gravity"] integerValue];
    NSInteger offsetY = [params[@"offsetY"] integerValue];
    NSString *bgColorStr = params[@"backgroundColor"] ?: @"";
    NSString *txtColorStr = params[@"textColor"] ?: @"";
    NSInteger fontSize = [params[@"fontSize"] integerValue];
    NSInteger borderRadius = [params[@"borderRadius"] integerValue];

    dispatch_async(dispatch_get_main_queue(), ^{
        UIWindow *window = [self keyWindow];
        if (!window) return;

        UIColor *bgColor = bgColorStr.length > 0 ? [self colorFromHexString:bgColorStr] : [[UIColor blackColor] colorWithAlphaComponent:0.75];
        UIColor *txtColor = txtColorStr.length > 0 ? [self colorFromHexString:txtColorStr] : [UIColor whiteColor];
        CGFloat fs = fontSize > 0 ? fontSize : 14.0;
        CGFloat radius = borderRadius > 0 ? borderRadius : 8.0;

        UILabel *label = [[UILabel alloc] init];
        label.text = text;
        label.textColor = txtColor;
        label.backgroundColor = bgColor;
        label.font = [UIFont systemFontOfSize:fs];
        label.textAlignment = NSTextAlignmentCenter;
        label.numberOfLines = 0;
        label.layer.cornerRadius = radius;
        label.clipsToBounds = YES;

        CGSize maxSize = CGSizeMake(window.bounds.size.width * 0.75, CGFLOAT_MAX);
        CGSize textSize = [text boundingRectWithSize:maxSize
                                             options:NSStringDrawingUsesLineFragmentOrigin
                                          attributes:@{NSFontAttributeName: label.font}
                                             context:nil].size;
        CGFloat width = textSize.width + 40;
        CGFloat height = textSize.height + 24;
        label.frame = CGRectMake(0, 0, width, height);

        CGFloat centerX = window.bounds.size.width / 2.0;
        CGFloat centerY;
        if (gravity == 48) {
            centerY = 100 + offsetY;
        } else if (gravity == 17) {
            centerY = window.bounds.size.height / 2.0 + offsetY;
        } else {
            centerY = window.bounds.size.height - 100 + offsetY;
        }
        label.center = CGPointMake(centerX, centerY);
        label.alpha = 0;

        [window addSubview:label];

        [UIView animateWithDuration:0.2 animations:^{
            label.alpha = 1;
        }];

        NSTimeInterval delay = (duration == 1) ? 3.5 : 2.0;
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [UIView animateWithDuration:0.3 animations:^{
                label.alpha = 0;
            } completion:^(BOOL finished) {
                [label removeFromSuperview];
            }];
        });
    });
}

- (void)log:(NSDictionary *)args {
    NSDictionary *params = [args[KR_PARAM_KEY] hr_stringToDictionary];
    NSString *content = params[@"content"] ?: @"";
    NSLog(@"[KRToastModule] %@", content);
}

- (UIWindow *)keyWindow {
    if (@available(iOS 15.0, *)) {
        for (UIScene *scene in [UIApplication sharedApplication].connectedScenes) {
            if ([scene isKindOfClass:[UIWindowScene class]]) {
                UIWindowScene *windowScene = (UIWindowScene *)scene;
                for (UIWindow *window in windowScene.windows) {
                    if (window.isKeyWindow) return window;
                }
            }
        }
    }
    return [UIApplication sharedApplication].keyWindow;
}

- (UIColor *)colorFromHexString:(NSString *)hexStr {
    NSString *clean = [hexStr stringByReplacingOccurrencesOfString:@"#" withString:@""];
    unsigned int hex = 0;
    [[NSScanner scannerWithString:clean] scanHexInt:&hex];

    if (clean.length == 8) {
        return [UIColor colorWithRed:((hex >> 24) & 0xFF) / 255.0
                               green:((hex >> 16) & 0xFF) / 255.0
                                blue:((hex >> 8) & 0xFF) / 255.0
                               alpha:(hex & 0xFF) / 255.0];
    }
    return [UIColor colorWithRed:((hex >> 16) & 0xFF) / 255.0
                           green:((hex >> 8) & 0xFF) / 255.0
                            blue:(hex & 0xFF) / 255.0
                           alpha:1.0];
}

@end
