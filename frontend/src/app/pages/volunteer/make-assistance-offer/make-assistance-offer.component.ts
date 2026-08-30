import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Removed FormArray import
import { ActivatedRoute, Router } from '@angular/router';
import { AssistanceOffer } from 'src/app/interfaces/assistanceoffer';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-make-assistance-offer',
  templateUrl: './make-assistance-offer.component.html',
  styleUrls: ['./make-assistance-offer.component.scss'],
})
export class MakeAssistanceOfferComponent implements OnInit {
  assistanceForm!: FormGroup;
  requestId: string | null | undefined;

  // Inject necessary services
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    // Initialize the form on component initialization
    this.route.paramMap.subscribe((params) => {
      this.requestId = params.get('requestId');
    });
    this.initializeForm();
  }

  initializeForm(): void {
    // Initialize the form with FormControls for aidTypes and donations
    this.assistanceForm = this.formBuilder.group({
      aidTypes: '',
      donations: '',
    });
  }

  // Method to submit the assistance request
  submitAssistanceRequest(): void {
    console.log('Submit assistance request clicked');
    // Get the token from local storage
    const token = localStorage.getItem('token');

    // Set headers with Authorization token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const requestOptions = { headers: headers };

    // Get the gateway URL from environment variables
    const gatewayUrl = environment.gatewayUrl;

    // Get form data and other required values
    const formData = this.assistanceForm.value;
    const userId = localStorage.getItem('userId');
    // const assistanceRequestId = this.getAssistanceRequestIdFromUrl();

    // Construct the request payload
    const requestPayload = {
      userId: userId,
      assistanceRequestId: this.requestId,
      aidTypes: [{ name: formData.aidTypes }],
      donations: [{ donationDescription: formData.donations }],
      dateOfferMade: this.getCurrentDate(),
      dateOfferAcceptedOrRejected: null,
    };

    // Send the POST request to the server
    this.http
      .post<AssistanceOffer>(
        `${gatewayUrl}/volunteer/assistanceoffers`,
        requestPayload,
        requestOptions,
      )
      .subscribe({
        next: (res) => {
          console.log('Assistance request submitted successfully');
          // redirect to volunteer page
          this.router.navigate(['/volunteer']);
        },
        error: (error) => {
          alert(error.error.message);
        },
      });
  }

  // Method to get the current date in the required format
  getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
