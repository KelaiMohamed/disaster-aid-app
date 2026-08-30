import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Skills} from "../../interfaces/skills";
import {Aidtype} from "../../interfaces/aidtype";

@Injectable({
  providedIn: 'root'
})
export class AitypeService {

  private gatewayUrl = environment.gatewayUrl;
  private baseUrl = `${this.gatewayUrl}/victim/aidtypes`;

  constructor(private http: HttpClient) {}

  getAllItems(): Observable<{ data: Aidtype[] }> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.get<{ data: Aidtype[] }>(this.baseUrl, requestOptions);
    } else {
      return new Observable<{ data: Aidtype[] }>((observer) =>
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

  postItem(aidtype:Aidtype): Observable<Aidtype> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.post<Aidtype>(this.baseUrl, aidtype, requestOptions);
    } else {
      return new Observable<Aidtype>((observer) =>
        observer.error('Token is missing')
      );
    }
  }

  updateItem(id: string, aidtype: Aidtype): Observable<Aidtype> {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      const requestOptions = { headers: headers };

      return this.http.put<Aidtype>(
        `${this.baseUrl}/${id}`,
        aidtype,
        requestOptions
      );
    } else {
      return new Observable<Aidtype>((observer) =>
        observer.error('Token is missing')
      );
    }
  }
}
