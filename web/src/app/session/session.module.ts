import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from '../material-module';
import { ErrorComponent } from './error/error.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SessionRoutingModule } from './session.routing';
import { SigninComponent } from './signin/signin.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    SessionRoutingModule
  ],
  declarations: [
    NotFoundComponent,
    ErrorComponent,
    SigninComponent
  ]
})

export class SessionModule {}
