import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
// import { FileSelectDirective } from 'ng2-file-upload';

import { MaterialModule } from './../material-module';
import { DashboardRoutes } from './dashboard.routing';
import { MenuComponent } from './menu/menu.component';
import { UserComponent } from './user/user.component';
import { MediaComponent } from './media/media.component';
import { GenreComponent } from './genre/genre.component';
import { ActorComponent } from './actor/actor.component';
import { CollectionComponent } from './collection/collection.component';
import { MediaDetailComponent } from './media-detail/media-detail.component';
import { MediaFormComponent } from './media-form/media-form.component';
import { EpisodesComponent } from './episodes/episodes.component';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    RouterModule.forChild(DashboardRoutes),
  ],
  declarations: [
    MenuComponent,
    UserComponent,
    // FileSelectDirective,
    MediaComponent,
    UserComponent,
    GenreComponent,
    ActorComponent,
    CollectionComponent,
    MediaDetailComponent,
    MediaFormComponent,
    EpisodesComponent
  ],
})

export class DashboardModule { }
