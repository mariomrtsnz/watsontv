import { UserUpdateDto } from './../../dto/user-update-dto';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { UserDto } from './../../dto/user-dto';
import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dialog-user',
  templateUrl: './dialog-user.component.html',
  styleUrls: ['./dialog-user.component.scss']
})
export class DialogUserComponent implements OnInit {

  edit: boolean;
  name: string;
  email: string;
  password: string;
  picture: string;
  userId: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private userService: UserService, public dialogRef: MatDialogRef<DialogUserComponent>) { }

  ngOnInit() {
    this.createForm();
    if (this.data) {
      this.edit = true;
      this.userId = this.data.user._id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedUser: UserUpdateDto = <UserUpdateDto>this.form.value;
      this.userService.edit(this.userId, editedUser).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this user.', 'Close', { duration: 3000 }));
    } else {
      const newUser: UserDto = <UserDto>this.form.value;
      this.userService.create(newUser).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this user.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.user.name, Validators.compose([Validators.required])],
        email: [this.data.user.email, Validators.compose([Validators.required, Validators.email])],
        picture: [this.data.user.picture, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])],
        email: [null, Validators.compose([Validators.required, Validators.email])],
        password: [null, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

  addUser() {
    const userCreateDto = new UserDto(this.email, this.name, this.password);
    this.userService.create(userCreateDto).subscribe(
      genre => {
        this.dialogRef.close('confirm');
      }
    );
  }

  editUser() {
    const userEditDto = new UserUpdateDto(this.email, this.name, this.picture);
    this.userService.edit(this.userId, userEditDto).subscribe(
      genre => {
        this.dialogRef.close('confirm');
      }
    );
  }

}
