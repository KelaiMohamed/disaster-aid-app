import { Component, OnInit } from '@angular/core';
import { AssistantrequestsService } from '../../../services/victim/assistantrequests.service';
import { ActivatedRoute } from '@angular/router';
import { SkillsService } from '../../../services/victim/skills.service';
import { AitypeService } from '../../../services/victim/aitype.service';
import { Assistancerequest } from '../../../interfaces/assistancerequest';
import { Skills } from '../../../interfaces/skills';
import { Aidtype } from '../../../interfaces/aidtype';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AssistanceOffer } from 'src/app/interfaces/assistanceoffer';

interface User {
  name: string;
  email: string;
}

interface AssistanceOfferDetails {
  AssistanceOffer: AssistanceOffer;
  User: User | null;
}

@Component({
  selector: 'app-assistant-details',
  templateUrl: './assistant-details.component.html',
  styleUrls: ['./assistant-details.component.scss'],
})
export class AssistantDetailsComponent implements OnInit {
  assistantRequestId: string | null = null;
  assistantRequestDetails: Assistancerequest | undefined;
  skills: Skills[] = [];
  aidtype: Aidtype[] = [];
  gatewayUrl = environment.gatewayUrl;
  assistanceOffers: AssistanceOfferDetails[] = [];

  constructor(
    private route: ActivatedRoute,
    private assistantservice: AssistantrequestsService,
    private skillsservice: SkillsService,
    private aidtypesrvc: AitypeService,
    private http: HttpClient,
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.assistantRequestId = params.get('id');
      if (this.assistantRequestId) {
        this.loadAssistantRequestDetails(this.assistantRequestId);
      }
      this.getAssistanceOffersForRequest();
    });
  }

  loadAssistantRequestDetails(id: string): void {
    this.assistantservice.getAssistantRequestById(id).subscribe(
      (response) => {
        this.assistantRequestDetails = response.data;
      },
      (error) => {
        console.error('Error fetching Assistant Request details:', error);
      },
    );
  }

  getAllSkills(): void {
    this.skillsservice.getAllItems().subscribe(
      (response) => {
        this.skills = response.data;
      },
      (error) => {
        console.error('Error fetching Skills:', error);
      },
    );
  }

  getAllAidtype(): void {
    this.aidtypesrvc.getAllItems().subscribe(
      (response) => {
        this.aidtype = response.data;
      },
      (error) => {
        console.error('Error fetching Skills:', error);
      },
    );
  }

  getAssistanceOffersForRequest(): void {
    if (this.assistantRequestId) {
      const token = localStorage.getItem('token');
      if (token) {
        const headers = new HttpHeaders().set(
          'Authorization',
          `Bearer ${token}`,
        );
        const requestOptions = { headers: headers };

        this.http
          .get<{ data: AssistanceOffer[] }>(
            `${this.gatewayUrl}/volunteer/assistanceoffers/request/${this.assistantRequestId}`,
            requestOptions,
          )
          .subscribe({
            next: (response) => {
              for (const assistanceOffer of response.data) {
                this.assistanceOffers?.push({
                  AssistanceOffer: assistanceOffer,
                  User: null,
                });
              }
            },
            error: (error) => {
              console.error('Error fetching Assistance Offers:', error);
            },
            complete: () => {
              this.loadUserDetailsForAssistanceOffers();
            },
          });
      } else {
        console.error('Token is missing');
      }
    }
  }

  loadUserDetailsForAssistanceOffers(): void {
    for (const offerDetails of this.assistanceOffers) {
      this.getUserForOffer(offerDetails.AssistanceOffer.userId)?.subscribe({
        next: (userResponse) => {
          offerDetails.User = userResponse;
        },
        error: (error) => {
          console.error('Error fetching User for Assistance Offer:', error);
        },
      });
    }
  }

  getUserForOffer(userId: string) {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };
      const userResponse = this.http.get<User>(
        `${this.gatewayUrl}/user/${userId}`,
        requestOptions,
      );
      console.log('User response:', userResponse);
      return userResponse;
    } else {
      return null;
    }
  }
}
