import { Directive } from '@angular/core';
import { EnderecoService } from '../service/endereco.service';
import { AbstractControl, NG_ASYNC_VALIDATORS, ValidationErrors } from '@angular/forms';
import { map, Observable } from 'rxjs';

@Directive({
  selector: '[appValidandoCep]',
  providers: [{
    provide: NG_ASYNC_VALIDATORS,
    useExisting: ValidandoCepDirective,
    multi: true
  }]
})
export class ValidandoCepDirective {

  constructor(private consultaCep: EnderecoService) { }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    const cep = control.value;
    return this.consultaCep.getConsultaCep(cep).pipe(map(
      (result: any) => result.erro ? {'validadorCep': true} : null
    ))
  }

}
