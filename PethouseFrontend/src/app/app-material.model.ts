import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatExpansionModule } from '@angular/material/expansion';
import { CdkAccordionModule } from '@angular/cdk/accordion';



@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatSnackBarModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    CdkAccordionModule
  ],
  exports: [
    MatDialogModule,
    MatSnackBarModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    CdkAccordionModule
  ]
})
export class MaterialModule { }
