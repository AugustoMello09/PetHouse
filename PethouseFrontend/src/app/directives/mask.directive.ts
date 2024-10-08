import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appMask]'
})
export class MaskDirective {

  @Input('appMask') mask: string = '';

  constructor(private el: ElementRef) { }

  @HostListener('input', ['$event'])
  onInput(event: Event): void {
    let value = this.el.nativeElement.value;
    value = value.replace(/\D/g, '');  
    this.el.nativeElement.value = this.applyMask(value, this.mask);
  }

  applyMask(value: string, mask: string): string {
    let maskedValue = '';
    let maskIndex = 0;
    let valueIndex = 0;

    while (maskIndex < mask.length && valueIndex < value.length) {
      if (mask[maskIndex] === '9') {
        maskedValue += value[valueIndex];
        valueIndex++;
      } else {
        maskedValue += mask[maskIndex];
      }
      maskIndex++;
    }

    return maskedValue;
  }

}
