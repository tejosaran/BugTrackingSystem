import { HttpInterceptorFn } from '@angular/common/http';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  // Skip authentication for login and register endpoints
  if (
    req.url.includes('/login') ||
    req.url.includes('/register')
  ) {
    return next(req);
  }

  // Get token from localStorage (you can change to sessionStorage if needed)
  const token = localStorage.getItem('jwtToken');

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(cloned);
  } else {
    console.warn('🚫 No token found — request blocked (unauthorized)');
    return next(req);
  }
};
