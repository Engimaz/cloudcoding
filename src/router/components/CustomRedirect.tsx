/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-05-17 13:01:17
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-06-24 17:53:34
 */
import { Navigate } from "react-router-dom";
import { RouteProps, Route } from "react-router-dom";
import React from "react";

interface RedirectProps extends RouteProps {
  redirectTo: string;// 重定向的url
  when: boolean;// 当条件为真的时候跳转
}

export default function CustomRedirect(redirectTo, when = true, ...rest): React.FC<RedirectProps> {
  return (
    <Route
      {...rest}
      render={(props) =>
        when ? (
          <Navigate to={{ to: redirectTo, state: { from: props.location } }} />
        ) : (
          <Component {...props} />
        )
      }
    />
  );
}