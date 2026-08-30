import { Component } from '@angular/core';

import { faHome } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-featured-content',
  templateUrl: './featured-content.component.html',
  styleUrls: ['./featured-content.component.scss'],
})
export class FeaturedContentComponent {
  faHome = faHome;
}
