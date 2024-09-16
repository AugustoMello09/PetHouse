import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';


@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatSnackBarModule
  ],
  exports: [
    MatDialogModule,
    MatSnackBarModule
  ]
})
export class MaterialModule { }
