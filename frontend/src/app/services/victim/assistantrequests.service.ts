import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Assistancerequest} from "../../interfaces/assistancerequest";

@Injectable({
  providedIn: 'root',
})
export class AssistantrequestsService {
  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/AssistantRequests`;

  constructor(private http: HttpClient) {}

  getAllItems(userId:string): Observable<{ data: Assistancerequest[] }> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.get<{ data: Assistancerequest[] }>(`${this.baseUrl}/user/${userId}`, requestOptions);
    } else {
      return new Observable<{ data: Assistancerequest[] }>((observer) =>
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

  postItem(assistanceRequest: Assistancerequest): Observable<Assistancerequest> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.post<Assistancerequest>(this.baseUrl, assistanceRequest, requestOptions);
    } else {
      return new Observable<Assistancerequest>((observer) =>
        observer.error('Token is missing')
      );
    }
  }
  getAssistantRequestById(id: string): Observable<any> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.get<any>(`${this.baseUrl}/${id}`, requestOptions);
    } else {
      return new Observable<any>((observer) =>
        observer.error('Token is missing')
      );
    }
  }

  updateItem(id: string, assistanceRequest: Assistancerequest): Observable<Assistancerequest> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.put<Assistancerequest>(
        `${this.baseUrl}/${id}`,
        assistanceRequest,
        requestOptions
      );
    } else {
      return new Observable<Assistancerequest>((observer) =>
        observer.error('Token is missing')
      );
    }
  }
}
