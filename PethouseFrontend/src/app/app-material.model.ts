import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDividerModule } from '@angular/material/divider';



@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatSnackBarModule,
    MatDividerModule
  ],
  exports: [
    MatDialogModule,
    MatSnackBarModule,
    MatDividerModule,
  ]
})
export class MaterialModule { }
