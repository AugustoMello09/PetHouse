import { NgModule } from '@angular/core';


import { MatDividerModule } from '@angular/material/divider';
import { MatDialogModule } from '@angular/material/dialog';


@NgModule({
  exports: [
    MatDividerModule,
    MatDialogModule
  ],
  imports: [
    MatDividerModule,
    MatDialogModule
  ]
})
export class MaterialModule { }