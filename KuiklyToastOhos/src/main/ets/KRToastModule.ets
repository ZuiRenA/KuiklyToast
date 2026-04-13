import { KRAny, KuiklyRenderBaseModule, KuiklyRenderCallback } from '@kuikly-open/render';
import { promptAction } from '@kit.ArkUI';

export class KRToastModule extends KuiklyRenderBaseModule {
  static readonly MODULE_NAME = 'KRToastModule';

  syncMode(): boolean {
    return false;
  }

  call(method: string, params: KRAny, callback: KuiklyRenderCallback | null): KRAny {
    switch (method) {
      case 'showToast':
        this.showToast(params);
        break;
      case 'log':
        this.doLog(params);
        break;
    }
    return null;
  }

  private showToast(params: KRAny): void {
    if (!params) return;
    const json: Record<string, Object> = JSON.parse(params as string);
    const text = (json['text'] as string) || '';
    const duration = (json['duration'] as number) || 0;
    const gravity = (json['gravity'] as number) || 80;
    const offsetY = (json['offsetY'] as number) || 0;

    let alignment: Alignment = Alignment.Bottom;
    if (gravity === 48) {
      alignment = Alignment.Top;
    } else if (gravity === 17) {
      alignment = Alignment.Center;
    }

    const toastDuration = duration === 1 ? 3500 : 2000;

    promptAction.showToast({
      message: text,
      duration: toastDuration,
      alignment: alignment,
      offset: { dx: 0, dy: offsetY }
    });
  }

  private doLog(params: KRAny): void {
    if (!params) return;
    const json: Record<string, Object> = JSON.parse(params as string);
    const content = (json['content'] as string) || '';
    console.info(`[KRToastModule] ${content}`);
  }

  onDestroy(): void {
  }
}
