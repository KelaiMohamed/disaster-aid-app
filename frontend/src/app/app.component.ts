import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'client';
  constructor(private route: Router) {}

  notLogInOrSignUp(): boolean {
    return this.route.url !== '/login' && this.route.url !== '/signup';
  }
}
