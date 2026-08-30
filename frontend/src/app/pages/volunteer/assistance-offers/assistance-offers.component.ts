import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AssistanceOffer } from 'src/app/interfaces/assistanceoffer';
@Component({
  selector: 'app-assistance-offers',
  templateUrl: './assistance-offers.component.html',
  styleUrls: ['./assistance-offers.component.scss'],
})
export class AssistanceOffersComponent implements OnInit {
  assistanceOffers: AssistanceOffer[] = [];
  userId: string | null | undefined;
  requestOptions: { headers: HttpHeaders } | undefined;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');

    // Set headers with Authorization token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.requestOptions = { headers: headers };

    // Fetch userId from localStorage
    this.userId = localStorage.getItem('userId');

    if (this.userId) {
      this.loadAssistanceOffers(this.userId);
    }
  }

  loadAssistanceOffers(userId: string): void {
    const gatewayUrl = environment.gatewayUrl;
    this.http
      .get<{ data: AssistanceOffer[] }>(
        `${gatewayUrl}/volunteer/assistanceoffers/user/${userId}`,
        this.requestOptions,
      )
      .subscribe({
        next: (res) => {
          this.assistanceOffers = res.data;
        },
        error: (err) => {
          alert(err);
        },
      });
  }
}
