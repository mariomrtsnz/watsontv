import { CollectionComponent } from './collection/collection.component';
import { GenreComponent } from './genre/genre.component';
import { ActorComponent } from './actor/actor.component';
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
      { path: 'genres', component: GenreComponent, canActivate: [AuthGuard] },
      { path: 'actors', component: ActorComponent, canActivate: [AuthGuard] },
      { path: 'collections', component: CollectionComponent, canActivate: [AuthGuard] }
    ]
  }
];
