import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { environment } from 'src/environments/environment';

import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

interface Response {
  status: string;
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService {
  constructor(
    private router: Router,
    private http: HttpClient,
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): boolean | Observable<boolean> {
    // Check if the user is authenticated
    return this.isAuthenticated().pipe(
      map((authenticated) => {
        if (authenticated) {
          return true;
        } else {
          // If not authenticated, redirect to the login page
          this.router.navigate(['/login']);
          return false;
        }
      }),
      catchError(() => {
        // Log the error or show a more user-friendly message
        console.error('An error occurred during authentication check.');
        this.router.navigate(['/login']);
        return of(false);
      }),
    );
  }

  private isAuthenticated(): Observable<boolean> {
    const gatewayUrl = environment.gatewayUrl;
    const authToken = localStorage.getItem('token');

    if (!authToken) {
      return of(false);
    }

    // Append the authentication endpoint to the gateway URL
    const authEndpoint = `${gatewayUrl}/auth/validate?token=${authToken}`;

    return this.http.get<Response>(authEndpoint).pipe(
      map((response) => response.status === 'success'),
      catchError((error) => {
        // Log the error or show a more user-friendly message
        console.error('An error occurred during authentication check.', error);
        return of(true); // Assume authenticated to prevent blocking the user
      }),
    );
  }
}
