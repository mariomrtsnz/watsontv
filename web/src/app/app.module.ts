import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from './material-module';
import { routes } from './app-routing.module';

import { AppComponent } from './app.component';
import { DialogGenreComponent } from './dialogs/dialog-genre/dialog-genre.component';
import { DialogActorComponent } from './dialogs/dialog-actor/dialog-actor.component';
import { DialogCollectionComponent } from './dialogs/dialog-collection/dialog-collection.component';
import { DialogDeleteGenreComponent } from './dialogs/dialog-delete-genre/dialog-delete-genre.component';
import { DialogDeleteActorComponent } from './dialogs/dialog-delete-actor/dialog-delete-actor.component';
import { DialogDeleteCollectionComponent } from './dialogs/dialog-delete-collection/dialog-delete-collection.component';
import { DialogDeleteMediaComponent } from './dialogs/dialog-delete-media/dialog-delete-media.component';
import { DialogUserComponent } from './dialogs/dialog-user/dialog-user.component';
import { DialogDeleteUserComponent } from './dialogs/dialog-delete-user/dialog-delete-user.component';
import { DialogMediaTypeComponent } from './dialogs/dialog-media-type/dialog-media-type.component';
import { DialogEpisodeComponent } from './dialogs/dialog-episode/dialog-episode.component';
import { DialogDeleteEpisodeComponent } from './dialogs/dialog-delete-episode/dialog-delete-episode.component';
import { DialogSeasonComponent } from './dialogs/dialog-season/dialog-season.component';
import { DialogDeleteSeasonComponent } from './dialogs/dialog-delete-season/dialog-delete-season.component';
import { DialogAddCastComponent } from './dialogs/dialog-add-cast/dialog-add-cast.component';

@NgModule({
  declarations: [
    AppComponent,
    DialogGenreComponent,
    DialogActorComponent,
    DialogCollectionComponent,
    DialogDeleteGenreComponent,
    DialogDeleteActorComponent,
    DialogDeleteCollectionComponent,
    DialogDeleteMediaComponent,
    DialogUserComponent,
    DialogDeleteUserComponent,
    DialogMediaTypeComponent,
    DialogEpisodeComponent,
    DialogDeleteEpisodeComponent,
    DialogSeasonComponent,
    DialogDeleteSeasonComponent,
    DialogAddCastComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  entryComponents: [DialogGenreComponent,
    DialogActorComponent,
    DialogCollectionComponent,
    DialogDeleteGenreComponent,
    DialogDeleteActorComponent,
    DialogDeleteCollectionComponent,
    DialogDeleteMediaComponent,
    DialogUserComponent,
    DialogDeleteUserComponent,
    DialogMediaTypeComponent,
    DialogEpisodeComponent,
    DialogDeleteEpisodeComponent,
    DialogSeasonComponent,
    DialogDeleteSeasonComponent,
    DialogAddCastComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
