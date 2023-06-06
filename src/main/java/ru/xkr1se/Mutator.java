package ru.xkr1se;

import lombok.Cleanup;
import lombok.val;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * @author xkr1se
 */
public class Mutator {
    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("[!] Wrong usage!");
            return;
        }

        val fileSource = new File(args[0]);

        if(!fileSource.exists()) {
            System.out.println("[!] File path " + fileSource.getAbsolutePath() + " not found.");
            return;
        }

        @Cleanup val jarSource = new JarFile(fileSource);
        @Cleanup val jarTarget = new JarOutputStream(Files.newOutputStream(Paths.get(args[1])));
        @Cleanup val bufferedJarTargetOutput = new BufferedOutputStream(jarTarget);

        val entries = jarSource.entries();

        int count = 0;
        JarEntry jarEntry;
        while (entries.hasMoreElements()) {
            jarEntry = entries.nextElement();
            val entryIn = jarSource.getInputStream(jarEntry);

            if(jarEntry.getName().endsWith(".class")) {
                jarEntry = new JarEntry(jarEntry.getName() + "/");
            }

            jarTarget.putNextEntry(jarEntry);

            int r;
            while ((r = entryIn.read()) != -1) {
                bufferedJarTargetOutput.write(r);
            }

            count++;

            bufferedJarTargetOutput.flush();
            jarTarget.closeEntry();
        }

        System.out.println("[*] Jar file mutated. Files processed " + count);
    }
}
