import { Component } from '@angular/core';

import {
  faPhone,
  faMessage,
  faLocationDot,
} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent {
  faPhone = faPhone;
  faMessage = faMessage;
  faLocationDot = faLocationDot;
}
