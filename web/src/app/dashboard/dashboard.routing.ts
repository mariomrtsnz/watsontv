import { Routes } from '@angular/router';

import { MenuComponent } from './menu/menu.component';
import { UserComponent } from './user/user.component';
import { MediaComponent } from './media/media.component';
import { AuthGuard } from '../guards/auth-guard';

export const DashboardRoutes: Routes = [
  {
    path: '',
    component: MenuComponent,
    children: [
      { path: '', component: MediaComponent, canActivate: [AuthGuard] },
      { path: 'users', component: UserComponent, canActivate: [AuthGuard] },
    ]
  }
];
