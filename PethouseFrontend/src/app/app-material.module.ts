import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule } from '@angular/material/dialog';


@NgModule({
  imports: [
    CommonModule,
    MatDialogModule
  ],
  exports: [
    MatDialogModule
  ]
})
export class AppMaterialModule { }
