import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { Auth } from '../services/auth';

export const AuthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const auth = inject(Auth);
  const platformId = inject(PLATFORM_ID);

  // ✅ Run only in browser environment
  if (isPlatformBrowser(platformId)) {
    const token = localStorage.getItem('jwtToken');

    // ✅ If token exists, allow route access
    if (token) {
      return true;
    }

    // ❌ No token found → block access & redirect
    window.alert('You must log in to access this page!');
    router.navigate(['']);
    return false;
  }

  // 🚫 Non-browser environment (SSR fallback)
  console.warn('AuthGuard executed on server – redirecting to login');
  router.navigate(['']);
  return false;
};
