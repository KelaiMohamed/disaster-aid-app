import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MakeAssistanceOfferComponent } from './make-assistance-offer.component';

@NgModule({
  declarations: [MakeAssistanceOfferComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  exports: [MakeAssistanceOfferComponent],
})
export class MakeAssistanceOfferModule {}
