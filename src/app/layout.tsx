import type { Metadata } from "next";
import "./globals.css";
import ReduxProvider from '@/redux/provider'
import Header from '@/app/header'
import Footer from '@/components/footer/index'
import { PrimeReactProvider } from "primereact/api";
import "primereact/resources/themes/lara-light-cyan/theme.css";


export const metadata: Metadata = {
  title: "云上编程",
  description: "不受环境限制的、愉悦的编程",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="zh-CN">
      <body>
        <ReduxProvider >
          <PrimeReactProvider value={{ unstyled: false }}>
            <section>
              <Header />
              {children}
              <Footer />
            </section>
          </PrimeReactProvider>

        </ReduxProvider>
      </body>
    </html>
  );
}
