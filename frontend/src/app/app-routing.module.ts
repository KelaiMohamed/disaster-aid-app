import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { AuthGuardService } from './services/auth-guard.service';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { VictimComponent } from './pages/victim/victim.component';
import { VolunteerComponent } from './pages/volunteer/volunteer.component';
import { SkillsComponent } from './pages/volunteer/skills/skills.component';
import { AssistanceOffersComponent } from './pages/volunteer/assistance-offers/assistance-offers.component';
import { MakeAssistanceOfferComponent } from './pages/volunteer/make-assistance-offer/make-assistance-offer.component';
import { AssistanceRequestsComponent } from './pages/volunteer/assistance-requests/assistance-requests.component';
import { AssistantDetailsComponent } from './pages/victim/assistant-details/assistant-details.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: 'about', component: AboutComponent },
  {
    path: 'volunteer',
    component: VolunteerComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'skills',
        component: SkillsComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: 'offers',
        component: AssistanceOffersComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: 'make-assistance-offer/:requestId',
        component: MakeAssistanceOfferComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: '',
        component: AssistanceRequestsComponent,
        canActivate: [AuthGuardService],
      },
    ],
  },
  {
    path: 'victim/assistant-details/:id',
    component: AssistantDetailsComponent,
    canActivate: [AuthGuardService],
  },

  {
    path: 'victim',
    component: VictimComponent,
    canActivate: [AuthGuardService],
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
