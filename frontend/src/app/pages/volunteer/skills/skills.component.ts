import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';

interface Skill {
  id?: number;
  name: string;
  userId: string;
}

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss'],
})
export class SkillsComponent implements OnInit {
  skills: Skill[] = [];
  newSkillForm!: FormGroup;
  userId!: string;
  requestOptions = { headers: new HttpHeaders({}) };

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
  ) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.requestOptions = { headers: headers };
    if (userId) {
      this.userId = userId;
    }

    this.loadSkills();
    this.newSkillForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      userId: [this.userId],
    });
  }

  loadSkills(): void {
    const gatewayUrl = environment.gatewayUrl;

    this.http
      .get<{ data: Skill[] }>(
        `${gatewayUrl}/volunteer/skills/user/${this.userId}`,
        this.requestOptions,
      )
      .subscribe((response) => {
        this.skills = response.data;
      });
  }

  addSkill(): void {
    const gatewayUrl = environment.gatewayUrl;

    this.http
      .post<Skill>(
        `${gatewayUrl}/volunteer/skills`,
        this.newSkillForm.value,
        this.requestOptions,
      )
      .subscribe({
        next: () => {
          this.loadSkills();
          this.newSkillForm.reset();
          console.log('Skill added successfully');
        },
        error: (error) => {
          alert(error.error.message);
        },
      });
  }

  deleteSkill(skillId: number | undefined): void {
    if (skillId) {
      const gatewayUrl = environment.gatewayUrl;

      this.http
        .delete(
          `${gatewayUrl}/volunteer/skills/${skillId}`,
          this.requestOptions,
        )
        .subscribe({
          next: () => {
            this.loadSkills();
          },
          error: (error) => {
            alert(error.error.message);
          },
        });
    }
  }
}
