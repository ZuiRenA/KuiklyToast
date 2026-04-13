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

    dispatch_async(dispatch_get_main_queue(), ^{
        UIWindow *window = [self keyWindow];
        if (!window) return;

        UILabel *label = [[UILabel alloc] init];
        label.text = text;
        label.textColor = [UIColor whiteColor];
        label.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.75];
        label.font = [UIFont systemFontOfSize:14];
        label.textAlignment = NSTextAlignmentCenter;
        label.numberOfLines = 0;
        label.layer.cornerRadius = 8;
        label.clipsToBounds = YES;

        CGSize maxSize = CGSizeMake(window.bounds.size.width - 80, CGFLOAT_MAX);
        CGSize textSize = [text boundingRectWithSize:maxSize
                                             options:NSStringDrawingUsesLineFragmentOrigin
                                          attributes:@{NSFontAttributeName: label.font}
                                             context:nil].size;
        CGFloat width = textSize.width + 32;
        CGFloat height = textSize.height + 20;
        label.frame = CGRectMake(0, 0, width, height);

        CGFloat centerX = window.bounds.size.width / 2.0;
        CGFloat centerY;
        if (gravity == 48) { // TOP
            centerY = 100 + offsetY;
        } else if (gravity == 17) { // CENTER
            centerY = window.bounds.size.height / 2.0 + offsetY;
        } else { // BOTTOM (default)
            centerY = window.bounds.size.height - 100 + offsetY;
        }
        label.center = CGPointMake(centerX, centerY);

        [window addSubview:label];

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

@end
