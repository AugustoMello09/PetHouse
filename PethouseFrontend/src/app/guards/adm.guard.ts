import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AdmGuard implements CanActivate {
  
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }
  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.authService.obterToken();
    if (token && !this.authService.jwtHelper.isTokenExpired(token)) {
      const decodedToken: any = jwtDecode(token);
      const roles = decodedToken.roles;
      if (roles === 'ROLE_ADM') {
        return true;
      }
    }

    
    this.router.navigate(['']);
    alert('Acesso negado, Não tem acesso para acessar esse recurso!!');
    return false;
  }
  
}
