import { Routes } from '@angular/router';
import { DashboardComponent } from './component/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  // Add more routes as needed
];
