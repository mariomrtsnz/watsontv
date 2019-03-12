export class UserUpdateDto {
    email: string;
    name: string;

    constructor(e: string, n: string) {
        this.email = e;
        this.name = n;
    }
}
