import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Assistancerequest} from "../../interfaces/assistancerequest";
import {Skills} from "../../interfaces/skills";

@Injectable({
  providedIn: 'root'
})
export class SkillsService {

  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/skills`;

  constructor(private http: HttpClient) {}

  getAllItems(): Observable<{ data: Skills[] }> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.get<{ data: Skills[] }>(this.baseUrl, requestOptions);
    } else {
      return new Observable<{ data: Skills[] }>((observer) =>
        observer.error('Token is missing')
      );
    }
  }

  deleteItem(id: string | undefined): Observable<any> {
    const token = localStorage.getItem('token');
    if (token && id) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.delete<any>(`${this.baseUrl}/${id}`, requestOptions);
    } else {
      return new Observable<any>((observer) =>
        observer.error('Token or ID is missing')
      );
    }
  }

  postItem(skills: Skills): Observable<Skills> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.post<Skills>(this.baseUrl, skills, requestOptions);
    } else {
      return new Observable<Skills>((observer) =>
        observer.error('Token is missing')
      );
    }
  }

  updateItem(id: string, skills: Skills): Observable<Skills> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.put<Skills>(
        `${this.baseUrl}/${id}`,
        skills,
        requestOptions
      );
    } else {
      return new Observable<Skills>((observer) =>
        observer.error('Token is missing')
      );
    }
  }
}
