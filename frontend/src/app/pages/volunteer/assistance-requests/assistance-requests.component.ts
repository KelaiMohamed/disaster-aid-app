import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, NgModule, OnInit } from '@angular/core';
import { AssistanceOffer } from 'src/app/interfaces/assistanceoffer';
import { Assistancerequest } from 'src/app/interfaces/assistancerequest';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-assistance-requests',
  templateUrl: './assistance-requests.component.html',
  styleUrls: ['./assistance-requests.component.scss'],
})
export class AssistanceRequestsComponent implements OnInit {
  assistanceRequests: Assistancerequest[] = [];
  assistanceOffers: AssistanceOffer[] | undefined;
  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/AssistantRequests`;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllAssistanceRequests();
  }

  getAllAssistanceRequests(): void {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');

    if (token && userId) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      // Fetch Assistance Requests
      this.http
        .get<{ data: Assistancerequest[] }>(`${this.baseUrl}`, requestOptions)
        .subscribe({
          next: (response) => {
            // Fetch Assistance Offers for the user
            this.assistanceRequests = response.data;
            console.log(this.assistanceRequests);
            // this.http
            //   .get<{ data: AssistanceOffer[] }>(
            //     `${this.gatewayUrl}/volunteer/assistanceoffers/user/${userId}`,
            //     requestOptions,
            //   )
            //   .subscribe({
            //     next: (res) => {
            //       this.assistanceOffers = res.data;
            //       // this.filterAssistanceRequests(response.data);
            //     },
            //     error: (err) => {
            //       console.error('Error fetching Assistance Offers:', err);
            //     },
            //   });
          },
          error: (error) => {
            console.error('Error fetching Assistance Requests:', error);
          },
        });
    } else {
      console.error('Token or userId is missing');
    }
  }

  // filterAssistanceRequests(assistanceRequests: Assistancerequest[]): void {
  //   if (this.assistanceOffers) {
  //     assistanceRequests.forEach((assistanceRequest) => {
  //       if (
  //         !this.assistanceOffers?.some(
  //           (offer: AssistanceOffer) =>
  //             offer.assistanceRequestId === assistanceRequest.id,
  //         )
  //       ) {
  //         this.assistanceRequests.push(assistanceRequest);
  //       }
  //     });
  //   } else {
  //     this.assistanceRequests = assistanceRequests;
  //   }
  // }
}
