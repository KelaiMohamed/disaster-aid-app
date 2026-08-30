import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Assistancerequest} from "../../interfaces/assistancerequest";

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/Locations`;


  constructor(private http: HttpClient) { }
  getAllItems(): Observable<{ data: Location[] }> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.get<{ data: Location[]  }>(this.baseUrl, requestOptions);
    } else {
      return new Observable<{ data: Location[] }>((observer) =>
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

  postItem(location: Location): Observable<Location> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.post<Location>(this.baseUrl, location, requestOptions);
    } else {
      return new Observable<Location>((observer) =>
        observer.error('Token is missing')
      );
    }
  }


  updateItem(id: string, location: Location): Observable<Location> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.put<Location>(
        `${this.baseUrl}/${id}`,
        location,
        requestOptions
      );
    } else {
      return new Observable<Location>((observer) =>
        observer.error('Token is missing')
      );
    }
  }
}
