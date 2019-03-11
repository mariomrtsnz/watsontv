import { Component, OnInit } from '@angular/core';
import { MatSnackBar, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  opened = false;
  over = 'over';
  loggedUser = null;
  alertMessage: string;

  constructor(private snackBar: MatSnackBar, public router: Router, public dialog: MatDialog,
    public authService: AuthenticationService, private userService: UserService) { }

  ngOnInit() {
    this.loggedUser = this.getLoggedUserInfo();
  }

  getLoggedUserInfo() {
    return {
      'username': this.authService.getName(),
      'email': this.authService.getEmail(),
      'picture': this.authService.getPicture()
    };
  }

}
