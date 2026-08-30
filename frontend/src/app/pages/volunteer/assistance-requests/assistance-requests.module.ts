import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // If you are using ngModel
import { AssistanceRequestsComponent } from './assistance-requests.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [AssistanceRequestsComponent],
  imports: [CommonModule, HttpClientModule, BrowserModule, FormsModule],
  exports: [AssistanceRequestsComponent],
})
export class AssistanceRequestsModule {}
