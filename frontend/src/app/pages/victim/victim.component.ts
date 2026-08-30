import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Assistancerequest } from '../../interfaces/assistancerequest';
import { environment } from '../../../environments/environment';

import { AssistantrequestsService } from '../../services/victim/assistantrequests.service';
import { SkillsService } from '../../services/victim/skills.service';
import { Skills } from '../../interfaces/skills';
import { AitypeService } from '../../services/victim/aitype.service';
import { Aidtype } from '../../interfaces/aidtype';

@Component({
  selector: 'app-victim',
  templateUrl: './victim.component.html',
  styleUrls: ['./victim.component.scss'],
})
export class VictimComponent implements OnInit {
  assistanceRequests: Assistancerequest[] = [];
  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/AssistantRequests`;
  showAddForm: boolean = false;
  newAssistanceRequest: Assistancerequest = {
    state: 'PENDING',
    date: this.getCurrentDate(),
    userId: '',
    skills: [{ name: '' }],
    aidType: [{ name: '' }],
    location: { address: '', street: '', city: '' },
    description: '',
  };

  skillstoSelect: string[] = [
    'Medical',
    'Search and Rescue',
    'Food Distribution',
    'Shelter Management',
    'Communication',
    'First Aid',
    'Search and Rescue',
    'Emergency Communication',
    'Shelter Management',
    'Community Outreach',
    'Medical Triage',
    'Logistics Planning',
    'Psychological Support',
    'Water Purification',
    'Food Distribution',
    'Field Cooking',
    'Public Health',
    'Emergency Shelter Construction',
    'Conflict Resolution',
    'Radio Operation',
    'GIS Mapping',
    'Volunteer Coordination',
    'Disaster Assessment',
    'Crisis Counseling',
  ];
  aidTypestoSelect: string[] = [
    'Financial Aid',
    'Medical Supplies',
    'Food Supplies',
    'Clothing',
    'Shelter',
    'Water Purification Kits',
    'Blankets',
    'Hygiene Kits',
    'Tents',
    'Emergency Food Rations',
    'First Aid Kits',
    'Generator Sets',
    'Portable Toilets',
    'Mosquito Nets',
    'Communication Devices',
    'Power Banks',
    'Solar Lanterns',
    'Construction Materials',
    'Fuel Supplies',
    'Crisis Counseling Services',
    'Search and Rescue Equipment',
    'Emergency Medicine',
    'Evacuation Transportation',
    'Community Outreach Programs',
  ];
  constructor(
    private http: HttpClient,
    private assistantservice: AssistantrequestsService,
    private skillsservice: SkillsService,
    private aidtypesrvc: AitypeService,
  ) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');

    if (userId) {
      this.getAllAssistantRequests();
    } else {
      console.error('User ID is missing in localStorage');
    }
  }
  getAllAssistantRequests(): void {
    const userId = localStorage.getItem('userId');

    if (userId) {
      console.log(localStorage.getItem('userId'));
      this.assistantservice.getAllItems(userId).subscribe(
        (response) => {
          this.assistanceRequests = response.data;
        },
        (error) => {
          console.error('Error fetching Assistance Requests:', error);
        },
      );
    } else {
      console.error('User ID is missing in localStorage');
    }
  }

  deleteIte(id: string | undefined): void {
    this.assistantservice.deleteItem(id).subscribe(
      () => {
        this.getAllAssistantRequests();
      },
      (error) => {
        console.error('Error deleting item:', error);
      },
    );
  }

  addAssistantRequest(): void {
    // Retrieve the user ID from localStorage
    const userId = localStorage.getItem('userId');

    // Check if the user ID is present
    if (!userId) {
      console.error('User ID is missing in localStorage');
      return;
    }

    // Assign the user ID to the new assistance request
    this.newAssistanceRequest.userId = userId;

    console.log(this.newAssistanceRequest);
    // Send the request
    this.assistantservice.postItem(this.newAssistanceRequest).subscribe({
      next: (response) => {
        console.log('Assistance Request added successfully:', response);

        // Update the list of assistant requests
        this.getAllAssistantRequests();

        // Hide the add form
        this.showAddForm = false;

        // Reset the form fields
        this.resetFormFields();
      },
      error: (error) => {
        console.error('Error adding Assistance Request:', error);

        // Log the error message received from the server
        console.error('Server error:', error.error);
      },
    });
  }

  // Helper function to reset form fields
  resetFormFields(): void {
    this.newAssistanceRequest = {
      state: 'PENDING',
      date: this.getCurrentDate(),
      userId: '',
      skills: [],
      aidType: [],
      location: { address: '', city: '', street: '' },
      description: '',
    };
  }

  // Helper function to get the current
  getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
